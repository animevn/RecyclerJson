package com.haanhgs.mynote;

public class Note {

    private String title;
    private String detail;
    private boolean isImportant;
    private boolean isTodo;
    private boolean isIdea;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isTodo() {
        return isTodo;
    }

    public void setTodo(boolean todo) {
        isTodo = todo;
    }

    public boolean isIdea() {
        return isIdea;
    }

    public void setIdea(boolean idea) {
        isIdea = idea;
    }
}
