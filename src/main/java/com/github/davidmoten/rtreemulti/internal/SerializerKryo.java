package com.github.davidmoten.rtreemulti.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.davidmoten.rtreemulti.Context;
import com.github.davidmoten.rtreemulti.Factory;
import com.github.davidmoten.rtreemulti.Node;
import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.Selector;
import com.github.davidmoten.rtreemulti.SelectorMinimalVolumeIncrease;
import com.github.davidmoten.rtreemulti.SelectorRStar;
import com.github.davidmoten.rtreemulti.Splitter;
import com.github.davidmoten.rtreemulti.SplitterQuadratic;
import com.github.davidmoten.rtreemulti.SplitterRStar;
import com.github.davidmoten.rtreemulti.geometry.Geometry;

public class SerializerKryo<T, S extends Geometry>
        implements com.github.davidmoten.rtreemulti.internal.Serializer<T, S> {

    private static final int VERSION = 1;

    @Override
    public void write(RTree<T, S> tree, OutputStream out) throws IOException {
        Kryo k = createKryo();
        k.writeObject(new Output(out), tree);
    }

    @Override
    public RTree<T, S> read(InputStream in) {
        Kryo k = createKryo();
        return k.readObject(new Input(in), RTree.class);
    }

    private static Kryo createKryo() {
        Kryo k = new Kryo();
        k.register(RTree.class, new Serializer<RTree<Object, Geometry>>() {

            @Override
            public RTree<Object, Geometry> read(Kryo k, Input input,
                    Class<? extends RTree<Object, Geometry>> cls) {
                int version = input.readShort();
                int dimensions = input.readShort();
                int minChildren = input.readShort();
                int maxChildren = input.readShort();
                Splitter splitter = input.readShort() == 0 ? SplitterQuadratic.INSTANCE
                        : SplitterRStar.INSTANCE;
                Selector selector = input.readShort() == 0 ? SelectorRStar.INSTANCE
                        : SelectorMinimalVolumeIncrease.INSTANCE;

                boolean rootNodeIsPresent = input.readBoolean();

                if (rootNodeIsPresent) {
                    @SuppressWarnings("unchecked")
                    Node<Object, Geometry> node = k.readObject(input, Node.class);
                    return new RTree<Object, Geometry>(Optional.<Node<Object, Geometry>>of(node), 0,
                            new Context<Object, Geometry>(dimensions, minChildren, maxChildren,
                                    selector, splitter, Factory.defaultFactory()));
                } else {
                    return new RTree<Object, Geometry>(Optional.<Node<Object, Geometry>>empty(), 0,
                            new Context<Object, Geometry>(dimensions, minChildren, maxChildren,
                                    selector, splitter, Factory.defaultFactory()));
                }
            }

            @Override
            public void write(Kryo k, Output output, RTree<Object, Geometry> tree) {
                output.writeShort(VERSION);
                output.writeShort(tree.dimensions());
                output.writeShort(tree.context().minChildren());
                output.writeShort(tree.context().maxChildren());
                short splitter = (short) ((tree.context().splitter() instanceof SplitterQuadratic)
                        ? 0
                        : 1);
                short selector = (short) ((tree.context().selector() instanceof SelectorRStar) ? 1
                        : 0);
                output.writeShort(splitter);
                output.writeShort(selector);
                // root Node isPresent
                output.writeBoolean(tree.root().isPresent());
                if (tree.root().isPresent()) {
                    k.writeObject(output, tree.root().get());
                }
            }
        });
        return k;
    }

}
