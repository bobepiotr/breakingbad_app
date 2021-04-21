package com.example.bb_app.models

import android.provider.ContactsContract

/*[{"char_id":1,
"name":"Walter White",
"birthday":"09-07-1958",
"occupation":["High School Chemistry Teacher","Meth King Pin"],
"img":"https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
"status":"Presumed dead",
"nickname":"Heisenberg",
"appearance":[1,2,3,4,5],
"portrayed":"Bryan Cranston",
"category":"Breaking Bad",
"better_call_saul_appearance":[]}]*/

class Character(
    val char_id: Int,
    val name: String,
    val birthday: String,
    val occupation: Array<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: Array<Int>,
    val portrayed: String,
    val category: String
)