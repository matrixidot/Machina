package io.github.matrixidot.machina.blocks.power.stirling_generator;

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

public class StirlingGeneratorScreen extends AbstractContainerScreen<StirlingGeneratorMenu> {
    private static final int ENERGY_LEFT = 152;
    private static final int ENERGY_WIDTH = 16;
    private static final int ENERGY_TOP = 9;
    private static final int ENERGY_BOTTOM = 79;
    private static final int ENERGY_HEIGHT = 70;

    private final int WHITE = Color.WHITE.getRGB();
    private final int RED = Color.RED.getRGB();

    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(Machina.MODID, "textures/gui/container/stirling_generator_gui.png");
    private final ResourceLocation FIRE_SPRITE = ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");

    public StirlingGeneratorScreen(StirlingGeneratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.inventoryLabelY = imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        graphics.blit(GUI, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int power = menu.getPower();
        int p = (int) ((power / (float) StirlingGeneratorTile.CAPACITY) * ENERGY_HEIGHT);
        graphics.fill(leftPos + ENERGY_LEFT, topPos + ENERGY_BOTTOM, leftPos + ENERGY_LEFT + ENERGY_WIDTH, topPos + ENERGY_BOTTOM - p, RED);

        if (menu.getBurnTime() > 0) {
            int l = Mth.ceil(menu.getFuelProgress() * 13) + 1;
            graphics.blitSprite(FIRE_SPRITE, 14, 14, 0, 14-l, leftPos + 80, topPos + 27 + 14 - l, 14, l);
        }

        if (isMouseAboveArea(mouseX, mouseY, leftPos, topPos, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            int num = StirlingGeneratorTile.GENERATE;
            if (menu.getPower() + StirlingGeneratorTile.GENERATE > StirlingGeneratorTile.CAPACITY || menu.getBurnTime() == 0)
                num = 0;
            graphics.drawString(font, num + " RF/t", leftPos + 9, topPos + 30, WHITE, true);
            graphics.drawString(font, menu.getBurnTime() / 20 + "s", leftPos + 9, topPos + 54, WHITE, true);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        if (isMouseAboveArea(x, y, leftPos, topPos, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            String text = menu.getPower() + " / " + StirlingGeneratorTile.CAPACITY + " RF";
            guiGraphics.renderComponentTooltip(font, List.of(Component.literal(text)), x, y);
        }
        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
