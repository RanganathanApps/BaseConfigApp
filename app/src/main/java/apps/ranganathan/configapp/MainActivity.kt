package apps.ranganathan.configapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apps.ranganathan.configlibrary.base.BaseAppActivity

class MainActivity : BaseAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showToast(this,"Hello world!")


    }
}
