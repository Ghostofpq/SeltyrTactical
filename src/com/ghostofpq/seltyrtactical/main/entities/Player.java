package com.ghostofpq.seltyrtactical.main.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ghostofpq
 * Date: 12/06/13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class Player {

    private String pseudo;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    private int grade;
}
