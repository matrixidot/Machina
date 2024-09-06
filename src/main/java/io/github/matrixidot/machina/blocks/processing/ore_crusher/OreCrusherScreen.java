package io.github.matrixidot.machina.blocks.processing.ore_crusher;

import io.github.matrixidot.machina.Machina;
import io.github.matrixidot.machina.tools.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.util.List;

public class OreCrusherScreen extends AbstractContainerScreen<OreCrusherMenu> {
    public static final int ENERGY_LEFT = 152;
    public static final int ENERGY_WIDTH = 16;
    public static final int ENERGY_TOP = 9;
    public static final int ENERGY_BOTTOM = 79;
    public static final int ENERGY_HEIGHT = 70;

    private final int WHITE = Color.WHITE.getRGB();
    private final int RED = Color.RED.getRGB();

    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "textures/gui/container/ore_crusher_gui.png");
    private final ResourceLocation CRUSH_SPRITE = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "textures/gui/sprites/container/crush.png");

    public OreCrusherScreen(OreCrusherMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.inventoryLabelY = imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        graphics.blit(GUI, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int power = menu.getPower();
        int p = (int) ((power / (float) OreCrusherTile.CAPACITY) * ENERGY_HEIGHT);
        graphics.fill(leftPos + ENERGY_LEFT, topPos + ENERGY_BOTTOM, leftPos + ENERGY_LEFT + ENERGY_WIDTH, topPos + ENERGY_BOTTOM - p, RED);


        int l = Mth.ceil(menu.progress() * 20) + 1;
        graphics.blitSprite(CRUSH_SPRITE, 18, 21, 0, 0, 21 - l, leftPos + 79, topPos + 37 + 21 - l, 18, l);

        if (isMouseAboveArea(mouseX, mouseY, leftPos, topPos, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            int num = menu.getPowerUsage();
            graphics.drawString(font, "-" + num + " RF/t", leftPos + 9, topPos + 30, WHITE, true);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        if (isMouseAboveArea(x, y, leftPos, topPos, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            int num = menu.getPowerUsage();
            guiGraphics.renderComponentTooltip(font, List.of(Component.literal("-" + num + " RF/t")), leftPos + 9, topPos + 30);
        }

        if (isMouseAboveArea(x, y, leftPos, topPos, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            String text = menu.getPower() + " / " + OreCrusherTile.CAPACITY + " RF";
            guiGraphics.renderComponentTooltip(font, List.of(Component.literal(text)), x, y);
        }
        super.renderTooltip(guiGraphics, x, y);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
