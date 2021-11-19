package com.semicolon.yasunnae.ui.coordinate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.base.BaseActivity
import com.semicolon.yasunnae.databinding.ActivityCoordinateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoordinateActivity : BaseActivity<ActivityCoordinateBinding>() {

    override val layoutResId: Int
        get() = R.layout.activity_coordinate

    private val coordinateViewModel: CoordinateViewModel by viewModels()

    override fun init() {
        binding.btnDoLocationCertificate.setOnClickListener {
            getLocation()
        }
    }

    override fun observe() {
        coordinateViewModel.successEvent.observe(this) {
            makeToast(getString(R.string.verified_location))
            finish()
        }

        coordinateViewModel.badRequestEvent.observe(this) {
            makeToast(getString(R.string.bad_request))
        }

        coordinateViewModel.retryEvent.observe(this) {
            makeToast(getString(R.string.try_it_later))
            print("dd")
        }

        coordinateViewModel.needToLoginEvent.observe(this) {
            finish()
            TODO("로그인 창 열기")
        }

        coordinateViewModel.unknownErrorEvent.observe(this) {
            makeToast(getString(R.string.unknown_error))
        }
    }

    private fun getLocation() {
        var curLocation: Location? = null
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        } else {
            curLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (curLocation != null) coordinateViewModel.saveCoordinate(
                longitude = curLocation.longitude,
                latitude = curLocation.latitude
            ) else {
                makeToast(getString(R.string.failed_get_location))
            }
        }
    }
}