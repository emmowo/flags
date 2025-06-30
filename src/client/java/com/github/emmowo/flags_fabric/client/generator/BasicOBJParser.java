package com.github.emmowo.flags_fabric.client.generator;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicOBJParser {

    /*
    OBJ LAYOUT

    v - vertex

    vn - vertex normals

    **vt** - vertex texture (uv?)

     s - smooth shading iirc

    f = vertex address mappings for faces. Each slash is a vertex, normal, then texture coord (if even present),
    -and the of these on one line is the type of polygon (3 = tri, 4 = quad)

    o = object declaration

    usemtl = material in .mtl file to use.

     */


    public class OBJModel{

        public List<OBJSubObject> objects = new ArrayList<>();

        public List<Vector3f> verticiesList = new ArrayList<>();
        public List<Vector3f> verticiesNormalList = new ArrayList<>();
        public List<Vector2f> verticiesTexList = new ArrayList<>();


    }


    private OBJSubObject currentObject = null;

    public OBJModel parse(BufferedReader reader){

        OBJModel inst = new OBJModel();

        int linenumber = 0;


        try {



            String line = reader.readLine();



            while (line != null){
                linenumber++;
                String[] splitlines = line.split(" ");


                switch (splitlines[0]){


                    case "o":
                        currentObject = new OBJSubObject(splitlines[1]);
                        inst.objects.add(currentObject);
                        break;


                    case "v":
                        inst.verticiesList.add(new Vector3f(
                                Float.parseFloat(splitlines[1]),
                                Float.parseFloat(splitlines[2]),
                                Float.parseFloat(splitlines[3])
                        ));
                        break;


                    case "vn":
                        inst.verticiesNormalList.add(new Vector3f(
                                Float.parseFloat(splitlines[1]),
                                Float.parseFloat(splitlines[2]),
                                Float.parseFloat(splitlines[3])
                        ));
                        break;


                    case "vt":
                        inst.verticiesTexList.add(new Vector2f(
                                Float.parseFloat(splitlines[1]),
                                Float.parseFloat(splitlines[2])
                        ));
                        break;

                    case "f":
                        List<String[]> entries = new ArrayList(); // [split0, split1[s0,s1,s2], split2[s0,s1,s2], etc ]
                        for(String segment : splitlines){
                            if(!Objects.equals(segment, splitlines[0]))
                                entries.add(segment.split("/"));
                        }

                        var data = new OBJFace();

                        for(String[] entry: entries){
                            // obj indicies are off
                            data.verticies.add(inst.verticiesList.get(Integer.parseInt(entry[0]) - 1));
                            data.vts.add(inst.verticiesTexList.get(Integer.parseInt(entry[1]) -1 ));
                            data.verticies_norm.add(inst.verticiesNormalList.get(Integer.parseInt(entry[2]) - 1));



                        }

                        currentObject.faces.add(data);
                        break;

                }

                line = reader.readLine();
            }







        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return inst;

    }

    public class OBJSubObject{

        public String name;

        /* // it turns out obj indicies are global, so the vertex/norm/tex lists are moved.

         */

        public List<OBJFace> faces = new ArrayList<>();

        public boolean presume_quads = true; // who cares about n-gons anyways?

        public OBJSubObject(String name){
            this.name = name;
        }




    }

    public class OBJFace{
        public List<Vector3f> verticies = new ArrayList<>();
        public List<Vector3f> verticies_norm = new ArrayList<>();
        public List<Vector2f> vts = new ArrayList<>();


    }

}
