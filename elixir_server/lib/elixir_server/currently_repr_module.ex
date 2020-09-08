defmodule ElixirServer.CurrentlyReprModule do

  defmodule CurrentlyBlock do
  @derive [Poison.Encoder]

  defstruct [:id, :time, :summary, :icon, :nearestStormDistance,
    :nearestStormBearing, :precipIntensity, :precipProbability, :temperature,
    :apparentTemperature, :dewPoint, :humidity, :pressure, :windSpeed, :windGust,
    :windBearing, :cloudCover, :uvIndex, :visibility, :ozone, :_links]

    # defp decode_links(%{"self" => self} = data), do: CurrentlyReprModule.decode(data)
    # defp decode_links(%{"latest" => latest} = data), do: CurrentlyReprModule.decode(data)
    # defp decode_links(%{"currently_collection" => currently_collection} = data), do: CurrentlyReprModule.decode(data)
  end

  defmodule Page do
    # @derive [Poison.Encoder]
    # @derive {Poison.Encoder, except: [:number]}

    defstruct [:size, :totalPages, :totalElements, :number]

    @type t :: %__MODULE__{
      size: integer,
      totalPages: integer,
      totalElements: integer,
      number: integer
    }

    @keys ~w(size totalPages totalElements number)
    def decode(%{} = map) do
        map
        |> Map.take(@keys)
        |> Enum.map(fn({k, v}) -> {String.to_existing_atom(k), v} end)
        |> Enum.map(&decode/1)
        |> fn(data) -> struct(__MODULE__, data) end.()
      end
      def decode({k, v}), do: {k, v}
  end

  defmodule Links do
    # @derive [Poison.Encoder]

    defstruct [:first, :self, :next, :last]

    @keys ~w(first self next last)
    def decode(%{} = map) do
        map
        |> Map.take(@keys)
        |> Enum.map(fn({k, v}) -> {String.to_existing_atom(k), v} end)
        |> Enum.map(&decode/1)
        |> fn(data) -> struct(__MODULE__, data) end.()
      end
      def decode({k, v}), do: {k, v}
  end


  defmodule CurrentlyCollection do
    defstruct [:id, :time, :summary, :icon, :nearestStormDistance,
      :nearestStormBearing, :precipIntensity, :precipProbability, :temperature,
      :apparentTemperature, :dewPoint, :humidity, :pressure, :windSpeed, :windGust,
      :windBearing, :cloudCover, :uvIndex, :visibility, :ozone, :_links]

    @keys ~w(id time summary icon nearestStormDistance
      nearestStormBearing precipIntensity precipProbability temperature
      apparentTemperature dewPoint humidity pressure windSpeed windGust
      windBearing cloudCover uvIndex visibility ozone _links)
    def decode(%{} = map) do
      map
      |> Map.take(@keys)
      |> Enum.map(fn({k, v}) -> {String.to_existing_atom(k), v} end)
      |> Enum.map(&decode/1)
      |> fn(data) -> struct(__MODULE__, data) end.()
    end

    def decode({:_links, links}) when is_list(links) do
      {:_links, Enum.map(links, &Links.decode/1)}
    end

    def decode({k, v}), do: {k, v}
  end


  defstruct [:currently_collection, :_links, :page]
  @keys ~w(currently_collection _links page)
  def decode(%{} = map) do
    map
    |> Map.take(@keys)
    |> Enum.map(fn({k, v}) -> {String.to_existing_atom(k), v} end)
    |> Enum.map(&decode/1)
    |> fn(data) -> struct(__MODULE__, data) end.()
  end

  def decode({:currently_collection, collection}) when is_list(collection) do
    {:currently_collection, Enum.map(collection, &CurrentlyCollection.decode/1)}
  end

  def decode({:_links, links}) when is_list(links) do
    {:_links, Enum.map(links, &Links.decode/1)}
  end

  def decode({:page, page}) do
    {:page, Enum.map(page, &Page.decode/1)}
  end
end
