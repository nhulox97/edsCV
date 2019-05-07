package com.nhulox.edscv.model


/** * Created by nhulox97 on 02,mayo,2019 */
class User {
    var uid: String? = null
    var email: String? = null
    var emailVerified: Boolean? = null
    var displayName: String? = null

    constructor(uid: String, email: String, displayName: String){
        this.uid = uid
        this.email = email
        this.emailVerified = false
        this.displayName = displayName
    }

    fun toMap(): Map<String, Any>{
        val result = HashMap<String, Any>()
        result["uid"] = uid!!
        result["email"] = email!!
        result["emailVerified"] = emailVerified!!
        result["displayName"] = displayName!!

        return result
    }
}