package com.newton.book.presentation.view.components

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import buku_app.composeapp.generated.resources.*
import com.newton.core.presentation.*
import org.jetbrains.compose.resources.*

@Composable
fun BookSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            shape = RoundedCornerShape(100),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue,
                focusedBorderColor = SandYellow,
            ),
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_hint)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                )
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onImeSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank()
                ) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.clear_search),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(100),
                    color = DesertWhite
                )
                .minimumInteractiveComponentSize()
        )
    }


}