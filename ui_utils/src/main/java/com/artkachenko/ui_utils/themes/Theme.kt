package com.artkachenko.ui_utils.themes

enum class Theme(
    val viewGroupTheme: ViewGroupTheme,
    val bottomNavigationViewTheme: BottomNavigationViewTheme,
    val textViewTheme: TextViewTheme,
    val imageViewTheme: ImageViewTheme,
    val chipTheme: ChipTheme,
    val searchViewTheme: SearchViewTheme
) {
    DARK(
        viewGroupTheme = ViewGroupTheme(
            backgroundColor = Themes.backgroundColorDark
        ),
        bottomNavigationViewTheme = BottomNavigationViewTheme(
            backgroundColor = Themes.backgroundColorDark
        ),
        textViewTheme = TextViewTheme(
            textColor = Themes.textColorDark,
            backgroundColor = Themes.backgroundColorDark
        ),
        imageViewTheme = ImageViewTheme(
            backgroundColor = Themes.backgroundColorDark
        ),
        chipTheme = ChipTheme(
            chipBackgroundColor = Themes.backgroundColorStateListDark,
            textColor = Themes.textColorStateListDark
        ),
        searchViewTheme = SearchViewTheme(
            textColor = Themes.textColorSecondary,
            hintColor = Themes.textColorSecondary,
            underlineColor = Themes.textColorSecondary,
            iconTint = Themes.textColorSecondary
        )
    ),
    LIGHT(
        viewGroupTheme = ViewGroupTheme(
            backgroundColor = Themes.backgroundColorLight
        ),
        bottomNavigationViewTheme = BottomNavigationViewTheme(
            backgroundColor = Themes.backgroundColorLight
        ),
        textViewTheme = TextViewTheme(
            textColor = Themes.textColorLight,
            backgroundColor = Themes.backgroundColorLight
        ),
        imageViewTheme = ImageViewTheme(
            backgroundColor = Themes.backgroundColorLight
        ),
        chipTheme = ChipTheme(
            chipBackgroundColor = Themes.backgroundColorStateListLight,
            textColor = Themes.textColorStateListLight
        ),
        searchViewTheme = SearchViewTheme(
            textColor = Themes.textColorSecondary,
            hintColor = Themes.textColorSecondary,
            underlineColor = Themes.textColorSecondary,
            iconTint = Themes.textColorSecondary
        )
    );
}