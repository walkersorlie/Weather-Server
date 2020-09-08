defmodule ElixirServerWeb.CurrentlyView do
  use ElixirServerWeb, :view
  use ElixirServer, :repr_module

  alias ElixirServerWeb.CurrentlyView


  def render("index.json", %{currently_blocks: currently_blocks}) do
    render_many(currently_blocks, CurrentlyView, "currently.json")
  end

  def render("show.json", %{currently: currently}) do
    render_one(currently, CurrentlyView, "currently.json")
  end

  def render("currently.json", %{currently: currently}) do
    %{currently: currently}
  end
end
