package com.example.photo_picker

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.photo_picker.ui.theme.Photo_PickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Photo_PickerTheme {
                ImageChange()
            }
        }
    }
}


@Composable
fun ImageChange() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var imageId by remember { mutableStateOf(R.drawable.cat1) }
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var selectedUriList by remember { mutableStateOf<List<Uri?>>(emptyList()) }

        val pickSingle =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    imageUri = uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        val pickMultiple =
            rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    selectedUriList = uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null
        )
        Button(onClick = {
            pickSingle.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
//            if (imageId == R.drawable.cat2) {
//                imageId = R.drawable.cat1
//            }
//            else{
//                imageId = R.drawable.cat2
//            }
        }) {
            Text(text = "단일 선택")
        }

        LazyRow {
            items(selectedUriList) { uri ->
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
//                        .blur(
//                            radiusX = 5.dp,
//                            radiusY = 5.dp,
//                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp)) 블러처리
//                        ),
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.ColorBurn)
                    //colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) 0f 채도 0%

                )
            }
        }

        Button(onClick = {
            pickMultiple.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        }) {
            Text(text = "복수 선택")
        }

        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .blur(
                    radiusX = 5.dp,
                    radiusY = 5.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                ),
            painter = painterResource(id = R.drawable.cat1),
            colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.ColorBurn),
            contentDescription = null
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Photo_PickerTheme {
        ImageChange()
    }
}