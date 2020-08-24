defmodule ElixirServerWeb.CurrentlyController do
  use ElixirServerWeb, :controller


  action_fallback ElixirServerWeb.FallbackController


  def fetch_data_point(data_point) do
    url = "http://192.168.0.98:8080/rest-service-0.0.1-SNAPSHOT/api/currently_collection/"

    case data_point do
      {:data_point, x} ->
        case HTTPoison.get(url<>"#{x}") do
          {:ok, %{status_code: 200, body: body}} ->
            body
            |> Poison.decode(keys: :atoms, as: %CurrentlyDataBLock{})
        end
      _ ->
        case HTTPoison.get(url) do
          {:ok, %{status_code: 200, body: body}} ->
            body
            |> Poison.decode(keys: :atoms, as: %CurrentlyDataBLock{})
        end
      end
    end


  def index(conn, _params) do
    with {:ok, currently} <- fetch_data_point(%{}) do
      render(conn, "index.html", currently: currently)
    end
  end

  def show(conn, %{"currently_id" => currently_id} = params) do
    with {:ok, currently} <- fetch_data_point(%{:data_point => currently_id}) do
      render(conn, "specific.html", currently: currently)
    end
  end
end
