/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.library.bookmarks.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import mozilla.components.concept.storage.BookmarkNode
import org.jetbrains.anko.image
import org.mozilla.fenix.R
import org.mozilla.fenix.library.LibrarySiteItemView
import org.mozilla.fenix.library.bookmarks.BookmarkState
import org.mozilla.fenix.library.bookmarks.BookmarkViewInteractor
import org.mozilla.fenix.library.bookmarks.inRoots

/**
 * Represents a folder with other bookmarks inside.
 */
class BookmarkFolderViewHolder(
    view: LibrarySiteItemView,
    interactor: BookmarkViewInteractor
) : BookmarkNodeViewHolder(view, interactor) {

    override fun bind(item: BookmarkNode, mode: BookmarkState.Mode, selected: Boolean) {

        containerView.displayAs(LibrarySiteItemView.ItemType.FOLDER)

        setClickListeners(mode, item, selected)

        if (!item.inRoots()) {
            setupMenu(item)
        } else {
            containerView.overflowView.visibility = View.GONE
        }

        containerView.changeSelected(selected)
        containerView.iconView.image = containerView.context.getDrawable(R.drawable.ic_folder_icon)?.apply {
            setTint(ContextCompat.getColor(containerView.context, R.color.primary_text_light_theme))
        }
        containerView.titleView.text = item.title
    }

    private fun setClickListeners(
        mode: BookmarkState.Mode,
        item: BookmarkNode,
        selected: Boolean
    ) {
        containerView.setOnClickListener {
            when {
                mode == BookmarkState.Mode.Normal -> interactor.expand(item)
                selected -> interactor.deselect(item)
                else -> interactor.select(item)
            }
        }

        containerView.setOnLongClickListener {
            if (mode == BookmarkState.Mode.Normal && !item.inRoots()) {
                interactor.select(item)
                true
            } else false
        }
    }
}
