package com.nowsprinting.intellij_mob.git

import com.nowsprinting.intellij_mob.config.MobProjectSettings
import git4idea.commands.GitCommand
import git4idea.repo.GitRepository

/**
 * git diff $remote/$branch --stat
 *
 * Must be called from `Task.Backgroundable#run()`.
 * If an error occurs, show a notification within this function.
 *
 * @param   remote          Target remote name
 * @param   branch          Target branch name
 * @param   repository      Git repository
 * @param   verbose         Add `--verbose` option (default: false)
 * @return  change list
 */
fun diffFrom(remote: String, branch: String, repository: GitRepository, verbose: Boolean = false): List<String> {
    return diffFrom("$remote/$branch", repository, verbose)
}

/**
 * git diff $branch --stat
 *
 * Must be called from `Task.Backgroundable#run()`.
 * If an error occurs, show a notification within this function.
 *
 * @param   branch          Target branch name
 * @param   repository      Git repository
 * @param   verbose         Add `--verbose` option (default: false)
 * @return  change list
 */
fun diffFrom(branch: String, repository: GitRepository, verbose: Boolean = false): List<String> {
    val command = GitCommand.DIFF
    val options = listOf(branch, "--stat")

    return git(command, options, repository, verbose)
}

/**
 * Check exist unpushed commit(s).
 *
 * Must be called from `Task.Backgroundable#run()`.
 * If an error occurs, show a notification within this function.
 *
 * @param   settings        Use remoteName, wipBranch
 * @param   repository      Git repository
 * @param   verbose         Add `--verbose` option (default: false)
 * @return  true if exist unpushed commit(s)
 *
 */
fun hasUnpushedCommit(settings: MobProjectSettings, repository: GitRepository, verbose: Boolean = false): Boolean {
    val commits = diffFrom(settings.remoteName, settings.wipBranch, repository, verbose)
    return commits.isNotEmpty()
}