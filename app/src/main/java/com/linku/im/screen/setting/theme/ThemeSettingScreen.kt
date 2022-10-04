package com.linku.im.screen.setting.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.linku.im.R
import com.linku.im.ktx.compose.ui.graphics.animated
import com.linku.im.screen.setting.SettingEvent
import com.linku.im.ui.components.Snacker
import com.linku.im.ui.components.ToolBar
import com.linku.im.ui.theme.LocalTheme

@Composable
fun ThemeSettingScreen(
    modifier: Modifier = Modifier,
    viewModel: ThemeSettingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(SettingEvent.Themes.Init)
    }
    val state = viewModel.readable
    Scaffold(
        snackbarHost = {
            Snacker(
                state = it,
                modifier = Modifier.fillMaxWidth()
            )
        },
        topBar = {
            ToolBar(
                actions = {},
                text = stringResource(id = R.string.profile_settings_theme),
                backgroundColor = LocalTheme.current.topBar.animated(),
                contentColor = LocalTheme.current.onTopBar.animated()
            )
        },
        modifier = modifier,
        backgroundColor = LocalTheme.current.background.animated(),
        contentColor = LocalTheme.current.onBackground.animated()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.themes) {
                    ThemeSelection(
                        theme = it,
                        currentTid = state.currentTheme,
                        modifier = Modifier.height(96.dp),
                        onClick = {
                            viewModel.onEvent(SettingEvent.Themes.SelectThemes(it.id))
                        }
                    )
                }
            }
        }
    }
}
