package com.ghostofpq.seltyrtactical.main.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 14/06/13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class Team {
    private String name;
    private List<Character> team;
}