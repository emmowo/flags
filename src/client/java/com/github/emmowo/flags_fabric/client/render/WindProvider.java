package com.github.emmowo.flags_fabric.client.render;

import net.minecraft.client.MinecraftClient;
import org.joml.Vector3f;

public interface WindProvider {

    Vector3f transformVertex(Vector3f i);

    // maybe have a 'uniform' getter be required instead?
    // if I make this feel like shader code, it'll be much easier to translate later (still hell given MC's rendering system).

    class FloorFlagWindProvider implements WindProvider{

        private final float seed;
        private final double time;

        public FloorFlagWindProvider(float seed, double time){
            this.seed = seed;
            this.time = time;
        }

        @Override
        public Vector3f transformVertex(Vector3f i) {
            Vector3f v = new Vector3f(i); // prevent reference stuff happening



            v.x += (float) (Math.sin(((time + this.seed) * 0.001) + v.x + Math.cos(v.z + v.x + ((this.seed + time) * 0.001))) * 0.225 * v.z * v.z * (v.z * (MinecraftClient.getInstance().world.isRaining() ? 1.2 : 1.0)  ));
            v.y += (float) (Math.sin(((time + this.seed) * 0.002) + v.y + Math.cos(v.z + v.y + ((this.seed + time) * 0.001))) * 0.15 * v.z * ((v.z + 1) * 0.5) );
            //v.z += (float) Math.sin(time + v.z);

            return v;

        }
    }

    class SmallFlagWindProvider implements WindProvider{

        private final float seed;
        private final double time;

        public  SmallFlagWindProvider(float seed, double time){
            this.seed = seed;
            this.time = time;
        }


        @Override
        public Vector3f transformVertex(Vector3f i) {
            Vector3f v = new Vector3f(i); // prevent reference stuff happening



            v.x += (float) (Math.sin(((time + this.seed) * 0.001) + v.x + Math.cos(v.z + v.x + ((this.seed + time) * 0.001))) * 0.15 * v.z * v.z * (v.z * (MinecraftClient.getInstance().world.isRaining() ? 1.2 : 1.0)  ));
            v.y += (float) (Math.sin(((time + this.seed) * 0.001) + v.y + Math.cos(v.z + v.y + ((this.seed + time) * 0.001))) * 0.075 * v.z * v.z);

            //v.z += (float) Math.sin(time + v.z);

            return v;
        }
    }


}
