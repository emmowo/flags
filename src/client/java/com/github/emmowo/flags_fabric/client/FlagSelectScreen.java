package com.github.emmowo.flags_fabric.client;

import com.github.emmowo.flags_fabric.FlagBlock;
import com.github.emmowo.flags_fabric.FlagBlockEntity;
import com.github.emmowo.flags_fabric.FlagSelectScreenHandler;
import com.github.emmowo.flags_fabric.Flags_fabric;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.tooltip.TooltipState;
import net.minecraft.client.gui.tooltip.WidgetTooltipPositioner;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class FlagSelectScreen extends HandledScreen<FlagSelectScreenHandler> {

    PlayerInventory invRef;


    public FlagSelectScreen(FlagSelectScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);


        invRef = inventory;

        this.backgroundWidth = 230;
        this.backgroundHeight = 219;
        handler.addListener(new ScreenHandlerListener() {
            public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
            }

            public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
            }
        });

    }

    private final List<ClickableWidget> buttons = Lists.<ClickableWidget>newArrayList();


    private <T extends ClickableWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add(button);
    }

    @Override
    protected void init() {
        // assign flag types correctly
        //TODO: add other generally accepted colour variants
        FLAG_L.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("lesbian_flag"))));
        FLAG_G.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("gay_flag"))));
        FLAG_B.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("bi_flag"))));
        FLAG_T.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("trans_flag"))));
        FLAG_NB.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("nonbinary_flag"))));
        FLAG_I.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("intersex_flag"))));
        FLAG_A_S.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("asexual_flag"))));
        FLAG_A_R.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("aromantic_flag"))));
        FLAG_PROGRESS.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("progress_flag"))));
        FLAG_PAN.set(DataComponentTypes.LORE,new LoreComponent(List.of(Text.of("pan_flag"))));




        super.init();

    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
    }


    private static ItemStack FLAG_L = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_G = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_B = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_T = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_NB = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_PAN = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_I = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_A_S = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_A_R = new ItemStack(Flags_fabric.HELD_FLAG);
    private static ItemStack FLAG_PROGRESS = new ItemStack(Flags_fabric.HELD_FLAG);






    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    int idx = 0;

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {

        this.buttons.clear();
        this.clearChildren();

        idx = 0;

        int i = (client.getWindow().getScaledWidth() - client.getWindow().getScaledWidth() / 4) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        //context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, i, j, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);

        context.fillGradient(i,j + 85,i + 47 + 26 + 66 + 44, j + 109 + 4 + 66, 0xAACCCCCC,0x00000000);

        context.getMatrices().pushMatrix();

        int TEXT_HEIGHT = j + 90;

        //TODO: use translatable text instead, list flag full names in a neat way
        context.drawText(client.textRenderer,Text.of("L"),i + 26, TEXT_HEIGHT,0xFFFFFF,true);


        List<ItemButtonWidget> flagList = new ArrayList<>();

        flagList.add(new ItemButtonWidget(FLAG_L,i + 20, j + 109, Text.of("Lesbian")));

        context.drawText(client.textRenderer,Text.of("G"),i + 47, TEXT_HEIGHT,0xFFFFFF,true);


        flagList.add(new ItemButtonWidget(FLAG_G, i + 41, j + 109, Text.of("Gay")));

        context.drawText(client.textRenderer,Text.of("B"),i + 47 + 22, TEXT_HEIGHT,0xFFFFFF,true);


        flagList.add(new ItemButtonWidget(FLAG_B, i + 41 + 22, j + 109,Text.of("Bisexual")));

        context.drawText(client.textRenderer,Text.of("T"),i + 47 + 44, TEXT_HEIGHT,0xFFFFFF,true);


        flagList.add(new ItemButtonWidget(FLAG_T, i + 41 + 44, j + 109,Text.of("Transgender")));

        context.drawText(client.textRenderer,Text.of("I"),i + 47 + 66, TEXT_HEIGHT,0xFFFFFF,true);


        flagList.add(new ItemButtonWidget(FLAG_I, i + 41 + 66, j + 109,Text.of("Intersex")));

        context.drawText(client.textRenderer,Text.of("A"),i + 47 + 66 + 22, TEXT_HEIGHT,0xFFFFFF,true);

        flagList.add(new ItemButtonWidget(FLAG_A_S, i + 41 + 66 + 22, j + 109,Text.of("Asexual")));

        context.drawText(client.textRenderer,Text.of("+"),i + 47 + 66 + 44, TEXT_HEIGHT,0xFFFFFF,true);

        flagList.add(new ItemButtonWidget(FLAG_PAN, i + 41 + 66 + 44, j + 109,Text.of("Pansexual")));

        // row 2

        flagList.add(new ItemButtonWidget(FLAG_PROGRESS,i + 41, j + 109 + 32, Text.of("Progress Pride") ));

        flagList.add(new ItemButtonWidget(FLAG_NB, i + 41 + 44, j + 109 + 32,Text.of("Nonbinary")));

        flagList.add(new ItemButtonWidget(FLAG_A_R, i + 41 + 66 + 22, j + 109 + 32,Text.of("Aromantic")));




        for (ItemButtonWidget widget : flagList){
            //widget.renderWidget(context,mouseX,mouseY,deltaTicks);
        }


        context.getMatrices().popMatrix();





    }

    public class ItemButtonWidget extends ClickableWidget{

        private ItemStack item;


        TooltipState state = new TooltipState();

        int i = 0;

        public ItemButtonWidget(ItemStack itemStack, int x, int y, Text tooltipPass) {
            super(x, y, 16,32, Text.of("test"));
            this.item = itemStack;
            addSelectableChild(this);
            addButton(this);
            idx++;
            i = idx;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

            context.getMatrices().pushMatrix();


            context.getMatrices().rotateAbout(0,1,25f);

            //context.getMatrices().translate(0,0,100);

            context.drawItem(item, this.getX() - 85, this.getY() + 18); // otherwise it gets from the screen? Also, Y is offset due to model's shape to make it align with the hitbox of the button.


            context.getMatrices().popMatrix();

            context.fillGradient(this.getX(),this.getY(),this.getX() + this.getWidth(), this.getY() + this.getHeight(),0xAACCCCCC,0x00000000); // eyeballing ARGB? Couldn't be me!




        }

        @Override
        public void onClick(double mouseX, double mouseY) {
           invRef.getSelectedStack().apply(DataComponentTypes.LORE,LoreComponent.DEFAULT,lore -> item.get(DataComponentTypes.LORE)); // set flag lore to reference (therefore changing the colour)
           item.applyChanges(item.getComponentChanges());
           var packet = new Flags_fabric.UpdateLorePacketC2S(item.get(DataComponentTypes.LORE));
           ClientPlayNetworking.send(packet);

           client.player.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT,0.7f,1.0f);


           if(FlagBlock.LAST_KNOWN_POS != null) {
               handlePossibleBlock(client.world, FlagBlock.LAST_KNOWN_POS, item.get(DataComponentTypes.LORE).lines().getFirst().getString());
           }
        }

        void handlePossibleBlock(World world, BlockPos pos,String text){


            var get = world.getBlockEntity(pos);



            if(get != null){
                System.out.println("wghuir");

                if(get instanceof FlagBlockEntity blockEntity){
                   //blockEntity.flagtype = text;
                   System.out.println("wehugghuir");
                   ClientPlayNetworking.send(new Flags_fabric.UpdateFlagTypePacketC2S(text,pos));
                }

            }

            FlagBlock.LAST_KNOWN_POS = null;

            close(); // otherwise the flag won't be able to change anymore.

        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
