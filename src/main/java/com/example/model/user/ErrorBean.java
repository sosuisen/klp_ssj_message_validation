package com.example.model.user;

import java.util.ArrayList;

import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;

@RedirectScoped
@Named
public class ErrorBean extends ArrayList<String> {
}
