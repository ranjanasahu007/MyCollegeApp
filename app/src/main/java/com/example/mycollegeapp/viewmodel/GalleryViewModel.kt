package com.example.mycollegeapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycollegeapp.models.GalleryModel
import com.example.mycollegeapp.utils.Constant.GALLERY
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class GalleryViewModel: ViewModel() {

    private val galleryref = Firebase.firestore.collection(GALLERY)
    private val storageRef = Firebase.storage.reference

    private val _isPostedGallery = MutableLiveData<Boolean>()
    val isPostedGallery: LiveData<Boolean> = _isPostedGallery

    private val _isDeletedGallery = MutableLiveData<Boolean>()
    val isDeletedGallery: LiveData<Boolean> = _isDeletedGallery

    private val _isDeletedImage = MutableLiveData<Boolean>()
    val isDeletedImage: LiveData<Boolean> = _isDeletedImage

    private val _galleryList= MutableLiveData<List<GalleryModel>>()
    val galleryList: LiveData<List<GalleryModel>> = _galleryList

    fun saveGalleryImage(uri: Uri, category: String, isCategory : Boolean) {
        _isPostedGallery.postValue(false)

        val randomUid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$GALLERY/${randomUid}.jpg")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                if (isCategory)
                    uploadCategory(it.toString(), category)
                else
                    updateImage(it.toString(), category)
            }
        }

    }

    private fun uploadCategory(image : String, category: String) {

        val map = mutableMapOf<String, Any>()
        map["category"] = category
        map["images"] = FieldValue.arrayUnion(image)

        galleryref.document(category).set(map)
            .addOnSuccessListener {
                _isPostedGallery.postValue(true)
            }
            .addOnFailureListener {
                _isPostedGallery.postValue(false)
            }

    }

    private fun updateImage(image : String, category: String) {

        galleryref.document(category).update("images", FieldValue.arrayUnion(image))
            .addOnSuccessListener {
                _isPostedGallery.postValue(true)
            }
            .addOnFailureListener {
                _isPostedGallery.postValue(false)
            }

    }


    fun getGallery() {
        galleryref.get()
            .addOnSuccessListener {
                val list = mutableListOf<GalleryModel>()

                for (doc in it) {
                    list.add(doc.toObject(GalleryModel::class.java))
                }

                _galleryList.postValue(list)
            }
    }


    fun deleteGallery(galleryModel: GalleryModel) {

        galleryModel.images!!.forEach {
            Firebase.storage.getReferenceFromUrl(it).delete()
        }

        galleryref.document(galleryModel.category!!).delete()
            .addOnSuccessListener {
                _isDeletedGallery.postValue(true)
            }
            .addOnFailureListener {
                _isDeletedGallery.postValue(false)
            }
    }

    fun deleteImage(category: String, image: String){
        galleryref.document(category).update("images", FieldValue.arrayRemove(image))
            .addOnSuccessListener {
                Firebase.storage.getReferenceFromUrl(image).delete()
                _isDeletedImage.postValue(true)
            }
            .addOnFailureListener {
                _isDeletedImage.postValue(false)
            }
    }

}