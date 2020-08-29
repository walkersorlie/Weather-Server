defmodule ElixirServerWeb.PageController do
  use ElixirServerWeb, :controller
  use ElixirServer, :repr_module


  action_fallback ElixirServerWeb.FallbackController

  def index(conn, _params) do

    url = "http://192.168.0.98:8080/rest-service-0.0.1-SNAPSHOT/api/currently_collection"

    case HTTPoison.get(url) do
      {:ok, %{status_code: 200, body: body}} ->
        body
        |> Poison.decode(keys: :atoms, as: %CurrentlyReprModule{})

        render(conn, "index.html", body: body)
    end
  end
end
