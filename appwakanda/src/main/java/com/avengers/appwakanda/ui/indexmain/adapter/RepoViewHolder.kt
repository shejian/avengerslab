/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.avengers.appwakanda.R
import com.avengers.appwakanda.db.room.entity.ContextItemEntity

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.left_text)

    private var repo: ContextItemEntity? = null

    init {
        /*    view.setOnClickListener {
                repo?.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                }
            }*/
    }

    fun bind(repo: ContextItemEntity?) {
        if (repo == null) {
            val resources = itemView.resources
            /*     name.text = resources.getString(R.string.loading)
                 description.visibility = View.GONE
                 language.visibility = View.GONE
                 stars.text = resources.getString(R.string.unknown)
                 forks.text = resources.getString(R.string.unknown)*/
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: ContextItemEntity) {
        this.repo = repo
        name.text = repo.title

        // if the description is missing, hide the TextView
        /*      var descriptionVisibility = View.GONE
              if (repo.description != null) {
                  description.text = repo.description
                  descriptionVisibility = View.VISIBLE
              }
              description.visibility = descriptionVisibility

              stars.text = repo.stars.toString()
              forks.text = repo.forks.toString()

              // if the language is missing, hide the label and the value
              var languageVisibility = View.GONE
              if (!repo.language.isNullOrEmpty()) {
                  val resources = this.itemView.context.resources
                  language.text = resources.getString(R.string.language, repo.language)
                  languageVisibility = View.VISIBLE
              }
              language.visibility = languageVisibility*/
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.indexlist_dbd_item_old, parent, false)
            return RepoViewHolder(view)
        }
    }
}