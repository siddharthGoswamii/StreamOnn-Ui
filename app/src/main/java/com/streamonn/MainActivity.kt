package com.streamonn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.streamonn.ui.theme.StreamOnnTheme
import androidx.compose.foundation.clickable
//Below imports for animation
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

private val AppBackground = Color(0xFF101010)
private val CardBackground = Color(0xFF202020)
private val AccentGreen = Color(0xFF1ED760)
private val PrimaryText = Color(0xFFFFFFFF)
private val SecondaryText = Color(0xFFB3B3B3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StreamOnnTheme {
                StreamOnnHome()
            }
        }
    }
}

@Composable
fun StreamOnnHome() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var currentScreen by remember { mutableStateOf("Home") }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF080808)
            ) {
                val tabs = listOf("Home", "Search", "Library")
                val icons = listOf("⌂", "⌕", "▤")

                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            currentScreen = title
                        },
                        icon = {
                            Text(
                                text = icons[index],
                                fontSize = 23.sp,
                                color = if (selectedTab == index) {
                                    AccentGreen
                                } else {
                                    SecondaryText
                                }
                            )
                        },
                        label = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) {
                                    PrimaryText
                                } else {
                                    SecondaryText
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                (
                        fadeIn(animationSpec = tween(250)) +
                                slideInHorizontally(
                                    animationSpec = tween(300),
                                    initialOffsetX = { it / 5 }
                                )
                        ).togetherWith(
                        fadeOut(animationSpec = tween(150)) +
                                slideOutHorizontally(
                                    animationSpec = tween(250),
                                    targetOffsetX = { -it / 6 }
                                )
                    ) using SizeTransform(clip = false)
            },
            label = "screen_transition"
        ) { screen ->

            when (screen) {
                "Home" -> HomeContent(
                    modifier = Modifier.padding(innerPadding),
                    onProfileClick = {
                        currentScreen = "Profile"
                    }
                )

                "Search" -> SimpleTabScreen(
                    title = "Search",
                    subtitle = "Find your next favourite song",
                    modifier = Modifier.padding(innerPadding)
                )

                "Library" -> SimpleTabScreen(
                    title = "Your Library",
                    subtitle = "Your music will live here",
                    modifier = Modifier.padding(innerPadding)
                )

                "Profile" -> ProfileScreen(
                    onBack = {
                        currentScreen = "Home"
                    },
                    onOptionClick = { option ->
                        currentScreen = option
                    },
                    modifier = Modifier.padding(innerPadding)
                )

                "Account", "Settings", "About StreamOnn", "Edit Profile" ->
                    DetailScreen(
                        title = screen,
                        subtitle = when (screen) {
                            "Account" -> "Manage your StreamOnn account."
                            "Settings" -> "Your app preferences will appear here."
                            "About StreamOnn" ->
                                "Your music, your world. StreamOnn is your personal music streaming experience."
                            else -> "Profile editing will be added here."
                        },
                        onBack = {
                            currentScreen = "Profile"
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val recentlyPlayed = listOf(
        MusicItem("After Hours", "The Weeknd", Color(0xFF692D45)),
        MusicItem("Divide", "Ed Sheeran", Color(0xFF385D45)),
        MusicItem("Starboy", "The Weeknd", Color(0xFF394B75)),
        MusicItem("Future Nostalgia", "Dua Lipa", Color(0xFF79452E))
    )

    val playlists = listOf(
        MusicItem("Daily Mix", "Made for you", Color(0xFF365C46)),
        MusicItem("Chill Vibes", "Take it easy", Color(0xFF414A78)),
        MusicItem("On Repeat", "Your favourites", Color(0xFF75414F)),
        MusicItem("Discover", "New music", Color(0xFF73512F))
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Welcome back",
                        fontSize = 14.sp,
                        color = SecondaryText
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Good evening 👋",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )
                }

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(AccentGreen)
                        .clickable {
                            onProfileClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "S",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp
                    )
                }
            }
        }

        item {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                placeholder = {
                    Text(
                        "Songs, artists, albums...",
                        color = SecondaryText
                    )
                },
                leadingIcon = {
                    Text("⌕", fontSize = 25.sp, color = SecondaryText)
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PrimaryText,
                    unfocusedTextColor = PrimaryText,
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AccentGreen
                )
            )
        }

        item {
            SectionHeading(
                title = "Recently played",
                action = "See all"
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(recentlyPlayed) { music ->
                    AlbumCard(music)
                }
            }
        }

        item {
            SectionHeading(
                title = "Made for you",
                action = "Explore"
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(playlists) { music ->
                    PlaylistCard(music)
                }
            }
        }

        item {
            Text(
                text = "Quick picks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )
        }

        items(
            listOf(
                MusicItem("Blinding Lights", "The Weeknd", Color(0xFF6B3448)),
                MusicItem("Perfect", "Ed Sheeran", Color(0xFF395E49)),
                MusicItem("As It Was", "Harry Styles", Color(0xFF66508A))
            )
        ) { music ->
            QuickPick(music)
        }
    }
}

@Composable
private fun SectionHeading(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
        Text(
            text = action,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = AccentGreen
        )
    }
}

@Composable
private fun AlbumCard(music: MusicItem) {
    Column(modifier = Modifier.width(145.dp)) {
        Box(
            modifier = Modifier
                .size(145.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(music.color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "♫",
                fontSize = 54.sp,
                color = PrimaryText
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = music.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryText,
            maxLines = 1
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = music.artist,
            fontSize = 13.sp,
            color = SecondaryText,
            maxLines = 1
        )
    }
}

@Composable
private fun PlaylistCard(music: MusicItem) {
    Column(modifier = Modifier.width(160.dp)) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(music.color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "♫",
                fontSize = 58.sp,
                color = PrimaryText
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = music.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = music.artist,
            fontSize = 13.sp,
            color = SecondaryText
        )
    }
}

@Composable
private fun QuickPick(music: MusicItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = CardBackground
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(music.color),
                contentAlignment = Alignment.Center
            ) {
                Text("♫", fontSize = 26.sp, color = PrimaryText)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = music.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = music.artist,
                    fontSize = 13.sp,
                    color = SecondaryText
                )
            }

            Text(
                text = "▶",
                fontSize = 18.sp,
                color = AccentGreen
            )
        }
    }
}

@Composable
private fun SimpleTabScreen(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = subtitle,
            fontSize = 16.sp,
            color = SecondaryText
        )
    }
}

@Composable
private fun DetailScreen(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {
        Text(
            text = "←  $title",
            color = PrimaryText,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onBack() }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = subtitle,
            color = SecondaryText,
            fontSize = 16.sp
        )
    }
}

private data class MusicItem(
    val title: String,
    val artist: String,
    val color: Color
)
