@file:Suppress("UnstableApiUsage")

package com.artkachenko.lint_checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.artkachenko.lint_checks.issues.HARDCODED_COLOR_ISSUE

class LintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(HARDCODED_COLOR_ISSUE)
    override val api: Int
        get() = CURRENT_API
}