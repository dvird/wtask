package com.example.wsc.module.home.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.wsc.R
import com.example.wsc.data.game.Game
import com.example.wsc.support.util.LoadingContent
import com.google.accompanist.appcompattheme.AppCompatTheme

@Composable
fun GamesScreen(
    onGameClick: (Game) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GamesViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        GamesContent(
            loading = uiState.isLoading,
            games = uiState.items,
            onRefresh = viewModel::refresh,
            onTaskClick = onGameClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun GamesContent(
    loading: Boolean,
    games: List<Game>,
    onRefresh: () -> Unit,
    onTaskClick: (Game) -> Unit,
    modifier: Modifier = Modifier
) {
    LoadingContent(
        loading = loading,
        empty = games.isEmpty() && !loading,
        emptyContent = { GamesEmptyContent(modifier) },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
        ) {
//            Text(
//                text = stringResource(currentFilteringLabel),
//                modifier = Modifier.padding(
//                    horizontal = dimensionResource(id = R.dimen.list_item_padding),
//                    vertical = dimensionResource(id = R.dimen.vertical_margin)
//                ),
//                style = MaterialTheme.typography.h6
//            )
            LazyColumn {
                items(games) { game ->
                    GameItem(
                        game = game,
                        onGameClicked = onTaskClick,
                        onCheckedChange = { }
                    )
                }
            }
        }
    }
}

@Composable
private fun GameItem(
    game: Game,
    onCheckedChange: (Boolean) -> Unit,
    onGameClicked: (Game) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.list_item_padding),
            )
            .clickable { onGameClicked(game) }
    ) {
        Text(
            text = (game.score.fulltime.home ?: 0).toString(),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.size(30.dp))

        Column() {
            Row() {
                val team1 = game.teams.home
                Text(
                    text = (game.score.fulltime.home ?: 0).toString(),
                    style = MaterialTheme.typography.body1,
                )
                AsyncImage(
                    model = team1.logo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = team1.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    ),
                )
            }
            Row() {
                val team2 = game.teams.away
                Text(
                    text = (game.score.fulltime.away ?: 0).toString(),
                    style = MaterialTheme.typography.body1,
                )
                AsyncImage(
                    model = team2.logo,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = team2.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    ),
                )
            }
        }
    }
}

@Composable
private fun GamesEmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = noGamesIconRes),
//            contentDescription = stringResource(R.string.no_data),
//            modifier = Modifier.size(96.dp)
//        )
//        Text(stringResource(id = noGamesLabel))
    }
}

@Preview
@Composable
private fun GamesContentPreview() {
    AppCompatTheme {
        Surface {
            GamesContent(
                loading = false,
                games = listOf(
//                    Game("Title 1", "Description 1"),
//                    Game("Title 2", "Description 2", true),
//                    Game("Title 3", "Description 3", true),
//                    Game("Title 4", "Description 4"),
//                    Game("Title 5", "Description 5", true)
                ),
                onRefresh = { },
                onTaskClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun GamesContentEmptyPreview() {
    AppCompatTheme {
        Surface {
            GamesContent(
                loading = false,
                games = emptyList(),
                onRefresh = { },
                onTaskClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun GamesEmptyContentPreview() {
    AppCompatTheme {
        Surface {
            GamesEmptyContent(
            )
        }
    }
}

@Preview
@Composable
private fun GamesItemPreview() {
    AppCompatTheme {
        Surface {
//            GameItem(
//                task = Game("Title", "Description"),
//                onTaskClick = { },
//                onCheckedChange = { }
//            )
        }
    }
}

@Preview
@Composable
private fun TaskItemCompletedPreview() {
    AppCompatTheme {
        Surface {
//            GameItem(
//                task = Game("Title", "Description", true),
//                onTaskClick = { },
//                onCheckedChange = { }
//            )
        }
    }
}
