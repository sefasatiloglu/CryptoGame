package com.example.cryptogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.example.cryptogame.databinding.ActivityMainBinding
import com.example.cryptogame.databinding.ActivityOyunEkraniBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {
    //Pozisyonlar
    private var anaKarakterX = 0.0f
    private var anaKarakterY = 0.0f
    private var dogeX = 0.0f
    private var dogeY = 0.0f
    private var bitcoinX = 0.0f
    private var bitcoinY = 0.0f
    private var etheriumX = 0.0f
    private var etheriumY = 0.0f


    //Boyutlar
    private var ekranGenisligi = 0
    private var ekranYuksekligi = 0
    private var anakarakterGenisligi = 0
    private var anakarakterYuksekligi = 0

    //Kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false
    private val timer = Timer()
    private var skor = 0

    private lateinit var tasarim:ActivityOyunEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_oyun_ekrani)

        tasarim.doge.x = -800.0f
        tasarim.doge.y = -800.0f
        tasarim.bitcoin.x = -800.0f
        tasarim.bitcoin.y = -800.0f
        tasarim.etherium.x = -800.0f
        tasarim.etherium.y = -800.0f

        tasarim.cl.setOnTouchListener(object :View.OnTouchListener{


            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                tasarim.textViewBasla.text = ""

                if (baslangicKontrol) {
                    //Ekrana dokundugunda
                    if (p1?.action == MotionEvent.ACTION_DOWN) {
                        Log.e("MotionEvent","ACTION_DOWN : Ekrana dokundu")
                        dokunmaKontrol = true
                    }
                    //Ekrani biraktiginda
                    if (p1?.action == MotionEvent.ACTION_UP) {
                        Log.e("MotionEvent","ACTION_UP : Ekrani birakti")
                        dokunmaKontrol = false
                    }

                }else{
                    baslangicKontrol = true
                    //bulundugu x konumu verir
                    anaKarakterX = tasarim.anaKarakter.x
                    anaKarakterY = tasarim.anaKarakter.y

                    anakarakterGenisligi = tasarim.anaKarakter.width
                    anakarakterYuksekligi = tasarim.anaKarakter.height
                    ekranGenisligi = tasarim.cl.width
                    ekranYuksekligi = tasarim.cl.height

                    timer.schedule(0,20){
                        //delay gecikme 0
                        //Gorsel nesnelerde degisiklik yapiyor Handler
                        Handler(Looper.getMainLooper()).post{
                            cisimleriHareketEttir()
                            carpismaKontrol()
                            val anakarakterHiz = ekranYuksekligi/60.0f
                            if (dokunmaKontrol) {

                                anaKarakterY-=anakarakterHiz

                            }else {
                                anaKarakterY+=anakarakterHiz

                            }

                            if (anaKarakterY <=0.0f) {
                                anaKarakterY = 0.0f
                            }
                            if (anaKarakterY>=ekranYuksekligi - anakarakterYuksekligi) {
                                anaKarakterY = (ekranYuksekligi - anakarakterYuksekligi).toFloat()
                            }
                            tasarim.anaKarakter.y = anaKarakterY
                        }
                    }

                }


                return true

            }


        })

        tasarim.anaKarakter.setOnClickListener{

            val intent = Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            startActivity(intent)
            finish()


        }

    }
    fun cisimleriHareketEttir() {
        dogeX-=ekranGenisligi/44.0f // Hizi bu ifade belirliyor
        // - ise sagdan-sola, + ise sagdan-sola haareket saglar
        bitcoinX-=ekranGenisligi/54.0f
        etheriumX-=ekranGenisligi/36.0f
        //Eger cisin en solda ise yani ekranin sonunda ise bastan baslamasi gereklidir
        if (dogeX < 0.0f) {
            dogeX = ekranGenisligi+20.0f
            dogeY = floor(Math.random()*ekranYuksekligi).toFloat()
            //Cismi ekranin en sagina yani ekranin dışında yönlendirir
        }
        //Cismin anlık x ve y konumunu belirler.
        tasarim.doge.x = dogeX
        tasarim.doge.y = dogeY
        if (bitcoinX < 0.0f) {
            bitcoinX = ekranGenisligi+20.0f
            bitcoinY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        tasarim.bitcoin.x = bitcoinX
        tasarim.bitcoin.y = bitcoinY
        if (etheriumX < 0.0f) {
            etheriumX = ekranGenisligi+20.0f
            etheriumY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        tasarim.etherium.x = etheriumX
        tasarim.etherium.y = etheriumY



    }
    fun carpismaKontrol() {

        val bitcoindaireMerkezX = bitcoinX + tasarim.bitcoin.width/2.0f
        val bitcoindaireMerkezY = bitcoinY + tasarim.bitcoin.height/2.0f
        if (0.0f <= bitcoindaireMerkezX && bitcoindaireMerkezX <= anakarakterGenisligi && anaKarakterY <= bitcoindaireMerkezY && bitcoindaireMerkezY <= anaKarakterY+anakarakterYuksekligi) {
            skor+=100
            bitcoinX = -10.0f
        }

        val etheriumdaireMerkezX = etheriumX + tasarim.etherium.width/2.0f
        val etheriumdaireMerkezY = etheriumY + tasarim.etherium.height/2.0f
        if (0.0f <= etheriumdaireMerkezX && etheriumdaireMerkezX <= anakarakterGenisligi && anaKarakterY <= etheriumdaireMerkezY && etheriumdaireMerkezY <= anaKarakterY+anakarakterYuksekligi) {
            skor+=50
            etheriumX = -10.0f
        }
        val dogeMerkezX = dogeX + tasarim.doge.width/2.0f
        val dogeMerkezY = dogeY + tasarim.doge.height/2.0f
        if (0.0f <= dogeMerkezX && dogeMerkezX <= anakarakterGenisligi && anaKarakterY <= dogeMerkezY && dogeMerkezY <= anaKarakterY+anakarakterYuksekligi) {

            etheriumX = -10.0f
            timer.cancel()
            val intent = Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }

        tasarim.textViewScore.text = skor.toString()
    }
}