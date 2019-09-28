package ir.ma.snippets.annotation.demo;

import ir.ma.snippets.annotation.api.Check;

import java.util.concurrent.CompletableFuture;

public class MyClass {

    @Check
    public CompletableFuture<String> getName() {
        return CompletableFuture.completedFuture("");
    }

}
