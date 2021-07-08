@file:Suppress("UnstableApiUsage")

package com.artkachenko.lint_checks.issues

import com.android.tools.lint.detector.api.*
import com.artkachenko.lint_checks.HARDCODED_COLOR
import com.artkachenko.lint_checks.detectors.HardcodedColorDetector

private const val DESCRIPTION = "Found hardcoded color"
private const val EXPLANATION =
    "Avoid using hardcoded colors and prefer ones found in the file" +
            "ui_utils/src/main/res/values/colors. If necessary add it to the file"

val HARDCODED_COLOR_ISSUE = Issue.create(
    id = HARDCODED_COLOR,
    briefDescription = DESCRIPTION,
    explanation = EXPLANATION,
    category = Category.TYPOGRAPHY,
    priority = 6,
    severity = Severity.ERROR,
    implementation = Implementation(
        HardcodedColorDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE
    )
)
