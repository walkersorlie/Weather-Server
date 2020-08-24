defmodule ElixirServerWeb.CurrentlyView do
  use ElixirServerWeb, :view


  def render("specific.html", %{currently: currently}) do
    %{data: render_one(currently, ElixirServerWeb.PageView, "currently.json")}  # probs have to change "currently.json"
  end
end
