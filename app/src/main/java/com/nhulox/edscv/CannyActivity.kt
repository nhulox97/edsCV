package com.nhulox.edscv

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import org.opencv.android.*
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.core.CvException
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CannyActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2{


    private val TAG: String = "CannyOpenCV::Activity"
    private val PERMISSIONS_REQUEST_CODE: Int = 123
    private lateinit var buttonTakePic: Button

    private lateinit var mOpenCvCameraView: CameraBridgeViewBase
    //private lateinit var mOpenCvCameraView: JavaCameraView
    private var mFiltered: Mat? = null

    private val mLoaderCallback = object:BaseLoaderCallback(this){
        override fun onManagerConnected(status: Int) {
            when (status){
                LoaderCallbackInterface.SUCCESS ->{
                    Log.i(TAG, "OpenCV loaded successfully")
                    mOpenCvCameraView.enableView()
                }
                else ->{
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPermissions()
        storagePermissions()

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_canny)

        mOpenCvCameraView= findViewById(R.id.tutorial1_activity_java_surface_view)
        mOpenCvCameraView.visibility = SurfaceView.VISIBLE

        mOpenCvCameraView.setCvCameraViewListener(this)

        buttonTakePic = findViewById(R.id.saveFrame)

        buttonTakePic.setOnClickListener { captureFrame() }

    }


    public override fun onPause() {
        super.onPause()
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView()
    }

    public override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback)
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        if (mFiltered == null)
            mFiltered = Mat(width, height, CvType.CV_8UC2)

    }

    override fun onCameraViewStopped() {
        if (mFiltered != null)
            mFiltered!!.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        if (mFiltered != null) {
            Imgproc.Canny(inputFrame.gray(), mFiltered, 127.0, 129.0)
            return mFiltered!!
        } else {
            return inputFrame.rgba()
        }
    }



    private fun getPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                PERMISSIONS_REQUEST_CODE)
            finish()
        }
    }

    private fun storagePermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                2)
            finish()
        }
    }


    private fun captureFrame() {
        var bmp: Bitmap? = null

        try{
            Log.d(TAG, "1")
            bmp = Bitmap.createBitmap(mFiltered!!.cols(), mFiltered!!.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(mFiltered, bmp)
        }catch (e: CvException){
            Log.d(TAG, e.message)
        }

        //mFiltered!!.release()

        var out: FileOutputStream? = null


        val filename = "frame.png"
        val sd = File("${Environment.getExternalStorageDirectory()}")
        var success = true

        if (!sd.exists()){
            Log.d(TAG, "2")
            success = sd.mkdir()
        }
        if(success){
            Log.d(TAG, "3")
            val dest = File(sd, filename)

            try{
                out = FileOutputStream(dest)
                bmp!!.compress(Bitmap.CompressFormat.PNG, 100, out)
                Log.d(TAG, "4")
            }catch (e: Exception){
                e.printStackTrace()
                Log.d(TAG, e.message)
            }finally {
                try{
                    if (out!= null){
                        out.close()
                        Log.d(TAG, "OK!")
                        Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: IOException){
                    e.printStackTrace()
                    Log.d(TAG, e.message)
                }
            }
        }
    }
}
