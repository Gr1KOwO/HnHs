package com.example.lessnon3_igor.presentation
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.lessnon3_igor.databinding.MapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata

class MapActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    private lateinit var binding: MapBinding

    private val mapTapListener = GeoObjectTapListener {
        val selectionMetadata: GeoObjectSelectionMetadata = it
            .geoObject
            .metadataContainer
            .getItem(GeoObjectSelectionMetadata::class.java)
        binding.mapView.mapWindow.map.selectGeoObject(selectionMetadata)

        if (it.geoObject.name != null){
            binding.apply{
                btnSelect.isEnabled = true
                address.isVisible = true
                address.text = it.geoObject.name
            }
        } else {
            binding.apply{
                btnSelect.isEnabled = false
                address.isVisible = false
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)

        binding = MapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.mapWindow.map.move(
            CameraPosition(
                Point(59.935493, 30.327392),
                /* zoom = */ 17.0f,
                /* azimuth = */ 0.0f,
                /* tilt = */ 0.0f
            ),
            Animation(Animation.Type.LINEAR, 3f),
            null,
        )

        binding.apply{
            mapView.mapWindow.map.addTapListener(mapTapListener)
            btnClose.setOnClickListener {
                finish()
            }
            btnSelect.setOnClickListener {
                val address = Intent()
                address.putExtra("address", binding.address.text)
                setResult(RESULT_OK, address)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}