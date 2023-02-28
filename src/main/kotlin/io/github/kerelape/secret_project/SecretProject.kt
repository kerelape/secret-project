package io.github.kerelape.secret_project

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

/**
 * SecretProject.
 *
 * @since 0.0.1
 */
@Suppress("UNUSED")
object SecretProject : ClientModInitializer {
    /**
     * Translation Key for open menu keybinding.
     *
     * @since 0.0.1
     */
    private const val TRANSLATION_KEY_OPEN_MENU_KEYBINDING: String = "secret_project.key.open_menu"

    /**
     * Open menu key binding.
     *
     * @since 0.0.1
     */
    private val OpenMenuKeyBinging: KeyBinding = KeyBinding(
        this.TRANSLATION_KEY_OPEN_MENU_KEYBINDING,
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_H,
        KeyBinding.UI_CATEGORY,
    )

    override fun onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(this.OpenMenuKeyBinging)
    }
}
