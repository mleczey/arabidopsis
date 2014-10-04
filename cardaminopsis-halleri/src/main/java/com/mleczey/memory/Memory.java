package com.mleczey.memory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("memories")
public class Memory {

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String memory() {
    return "<p>memories</p>";
  }
}
