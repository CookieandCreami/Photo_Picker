package com.example.photo_picker

import android.net.Uri
import android.os.Bundle
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        //save
        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            imageUri = uri
        }

        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape),
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null
        )
        Button(onClick = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
//            if (imageId == R.drawable.cat2) {
//                imageId = R.drawable.cat1
//            }
//            else{
//                imageId = R.drawable.cat2
//            }
        }) {
            Text(text = "변경")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Photo_PickerTheme {
        ImageChange()
    }
}