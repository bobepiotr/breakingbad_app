package com.example.bb_app.models
import java.io.Serializable

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

data class Character(
        val char_id: Int,
        val name: String,
        var birthday: String,
        val occupation: List<String>,
        val img: String,
        val status: String,
        val nickname: String,
        val appearance: List<Int>,
        val portrayed: String,
        val category: String
): Serializable {

        fun fixCharacter() {
                birthday = when {
                        birthday.isNullOrEmpty() -> {"Unknown"}
                        birthday.length > 9 -> { birthday.substring(0, 10)}
                        else -> {birthday}
                }
        }
}
