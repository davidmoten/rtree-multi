package com.github.davidmoten.rtreemulti.internal;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.davidmoten.rtreemulti.Node;
import com.github.davidmoten.rtreemulti.NonLeaf;
import com.github.davidmoten.rtreemulti.RTree;
import com.github.davidmoten.rtreemulti.SelectorRStar;
import com.github.davidmoten.rtreemulti.SplitterQuadratic;
import com.github.davidmoten.rtreemulti.geometry.Geometry;

public class SerializerStandard<T, S extends Geometry> implements Serializer<T, S> {

    private static final int VERSION = 1;

    @Override
    public void write(RTree<T, S> tree, OutputStream out) throws IOException {
        DataOutputStream d;
        if (out instanceof DataOutputStream) {
            d = (DataOutputStream) out;
        } else {
            d = new DataOutputStream(out);
        }
        // global fields are
        // version
        // dimensions
        // minChildren
        // maxChildren
        // splitter
        // selector
        d.writeShort(VERSION);
        d.writeShort(tree.dimensions());
        d.writeShort(tree.context().minChildren());
        d.writeShort(tree.context().maxChildren());
        short splitter = (short) ((tree.context().splitter() instanceof SplitterQuadratic) ? 0 : 1);
        short selector = (short) ((tree.context().selector() instanceof SelectorRStar) ? 1 : 0);
        d.writeShort(splitter);
        d.writeShort(selector);

        if (tree.root().isPresent()) {
            writeNode(tree.root().get(), d);
        }
    }


    private static <T, S extends Geometry> void writeNode(Node<T, S> node, DataOutputStream d) throws IOException {
        if (node.isLeaf()) {
            // write leaf
        } else {
            NonLeaf<T, S> nd = (NonLeaf<T, S>) node;
            for (Node<T, S> n: nd.children()) {
                writeNode(n, d);
            }
            d.writeShort(nd.count());
        }
    }


    @Override
    public RTree<T, S> read(InputStream in) {
        // TODO Auto-generated method stub
        return null;
    }

}
