package com.shashank.memebase.globals

import android.net.Uri


class Constants {

    object AnimationProperties {
        const val MAX_TRANSLATION_Y = -200f
        const val DURATION : Long = 400
    }

    object FieldValidationsProperties {
        const val MIN_CHARACTERS_FOR_PASSWORD = 9
        const val MAX_CHARACTERS_FOR_PASSWORD : Long = 16
    }

    object ApiProperties {
        const val APP_URL: String = "https://api.imgflip.com/"
        const val TABLE_NAME_MEME_MODEL: String = "mememodel"
        const val DEFAULT_REQUEST_TIMEOUT : Long =  400
        const val ACCESS_TOKEN_KEY = "AccessToken"
    }

    enum class AgendaDialog{
        AGENDA_TYPE,
        AGENDA_STATE,
        AGENDA_PRE_TIMERS,
        AGENDA_COMPRESSOR_MODULES,
        AGENDA_MEMES_MODULES
    }

    object VideoProperties{
        var uri : Uri? = null
    }
}
