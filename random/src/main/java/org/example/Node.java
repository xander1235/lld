package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
    String tag;
    String content;

    Map<String, String> attributes;

    List<Node> children = new ArrayList<>();

}
