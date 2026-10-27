
package com.streamonn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ProfileBackground = Color(0xFF101010)
private val ProfileCard = Color(0xFF202020)
private val ProfileGreen = Color(0xFF1ED760)
private val ProfileText = Color.White
private val ProfileSecondary = Color(0xFFB3B3B3)

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onOptionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ProfileBackground)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "←",
                color = ProfileText,
                fontSize = 30.sp,
                modifier = Modifier.clickable { onBack() }
            )

            Text(
                text = "Your Profile",
                color = ProfileText,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(ProfileGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    color = Color.Black,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Siddharth",
                color = ProfileText,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "StreamOnn listener",
                color = ProfileSecondary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileOption(
            icon = "👤",
            title = "Edit Profile",
            subtitle = "View and edit your profile",
            onClick = { onOptionClick("Edit Profile") }
        )

        ProfileOption(
            icon = "⚙",
            title = "Account",
            subtitle = "Manage your account",
            onClick = { onOptionClick("Account") }
        )

        ProfileOption(
            icon = "🎛",
            title = "Settings",
            subtitle = "App preferences",
            onClick = { onOptionClick("Settings") }
        )

        ProfileOption(
            icon = "ⓘ",
            title = "About StreamOnn",
            subtitle = "Learn more about the app",
            onClick = { onOptionClick("About StreamOnn") }
        )
    }
}

@Composable
private fun ProfileOption(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ProfileCard),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = ProfileText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                color = ProfileSecondary,
                fontSize = 13.sp
            )
        }

        Text(
            text = "›",
            color = ProfileSecondary,
            fontSize = 28.sp
        )
    }
}
