package net.ritasister.wgrp.api.manager;

import net.ritasister.wgrp.api.manager.tools.SuperPickaxe;

/**
 * A manager interface for handling WorldEdit-related tools and selections.
 * * <p>This manager acts as an adapter layer over the WorldEdit API, enabling
 * advanced manipulation of developer/staff tools such as the Super Pickaxe.</p>
 *
 * <p>This interface utilizes the Curiously Recurring Template Pattern (CRTP)
 * by extending {@code SuperPickaxe<ToolsAdapterManager>}. This structure ensures
 * a Fluent API contract, allowing tool-builder methods to return the specific
 * type of this manager for seamless method chaining.</p>
 *
 * @see SuperPickaxe
 */
public interface ToolsAdapterManager<P> extends SuperPickaxe<P> {

}
