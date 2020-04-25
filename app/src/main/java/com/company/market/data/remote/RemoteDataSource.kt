package com.company.market.data.remote

import com.company.market.data.Product
import com.company.market.data.Result
import com.company.market.data.Result.*
import com.company.market.data.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class RemoteDataSource internal constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun createUser(){}

    fun getUserProfile(): Result<UserProfile?> {
        val uid = auth.uid
        var userProfile: UserProfile?
        lateinit var result: Result<UserProfile?>
        if (uid != null) {
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    userProfile = documentSnapshot.toObject<UserProfile>()
                    result = Success(userProfile)
                }
                .addOnFailureListener {
                    result = Error(it)
                }
        }
        return result
    }

    fun updateUserProfile(userProfile: HashMap<Any, Any>): Result<Boolean>{
        val uid = auth.uid
        lateinit var result: Result<Boolean>
        if(uid != null){
            db.collection("users").document(uid)
                .set(userProfile)
                .addOnSuccessListener {
                    result = Success(true)
                }
                .addOnFailureListener {
                    result = Error(it)
                }
        }
        return result
    }

    fun getProductsFromFirestore(): Result<List<Product>>{

        lateinit var result: Result<List<Product>>

        db.collection("products")
            .whereEqualTo("inStock", true)
            .get()
            .addOnSuccessListener { document ->
                val product = document.toObjects<Product>()
                result = Success(product)
            }
            .addOnFailureListener {
                result = Error(it)
            }

        return result
    }
}