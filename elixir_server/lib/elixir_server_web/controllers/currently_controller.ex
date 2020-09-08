defmodule ElixirServerWeb.CurrentlyController do
  use ElixirServerWeb, :controller
  use ElixirServer, :repr_module


  action_fallback ElixirServerWeb.FallbackController


  def make_request(url) do
    case HTTPoison.get(url) do
      {:ok, %HTTPoison.Response{status_code: 200, body: body}} ->
        case Poison.decode(body, as: %CurrentlyReprModule.CurrentlyBlock{}) do
            {:ok, decoded} -> {:ok, decoded}
            {:error, error} -> {:error, error}
          end
      {:ok, %HTTPoison.Response{status_code: status_code}} ->
        {:error, status_code}
      {:error, error} ->
        {:error, error}
      end
    # case HTTPoison.get(url) do
    #   {:ok, %HTTPoison.Response{status_code: 200, body: body}} ->
    #     body
    #     |> Jason.decode()
    #     # |> CurrentlyReprModule.decode()
    #   end
  end

  def make_index_request(url) do
    case HTTPoison.get(url) do
      {:ok, %HTTPoison.Response{status_code: 200, body: body}} ->
        Poison.decode(body, as: %{
          "_embedded" => %{
            "currently_collection" => [%CurrentlyReprModule.CurrentlyBlock{}]
            },
          "_links" => %CurrentlyReprModule.Links{},
          "page" => [%CurrentlyReprModule.Page{}]
        })
        # CurrentlyReprModule.decode(body)
      {:ok, %HTTPoison.Response{status_code: status_code}} ->
        {:error, status_code}
      {:error, error} ->
        {:error, error}
      end
    # case HTTPoison.get(url) do
    #   {:ok, %HTTPoison.Response{status_code: 200, body: body}} ->
    #     body
    #     |> Jason.decode()
    #     # |> CurrentlyReprModule.decode()
    #   end
  end


  def index(conn, _params) do
    url = "http://192.168.0.98:8080/rest-service-0.0.1-SNAPSHOT/api/currently_collection/"
    with {:ok, currently_blocks} <- make_index_request(url) do
      render(conn, "index.json", currently_blocks: currently_blocks)
    end
  end

  def show(conn, %{"id" => currently_id}) do
    url = "http://192.168.0.98:8080/rest-service-0.0.1-SNAPSHOT/api/currently_collection/"<>"#{currently_id}"
    with {:ok, currently} <- make_request(url) do
      render(conn, "show.json", currently: currently)
    end
  end
end
