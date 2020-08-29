defmodule ElixirServer.CurrentlyReprModule do
  @derive [Poison.Encoder]

  defstruct [:id, :time, :summary, :icon, :nearestStormDistance,
    :nearestStormBearing, :precipIntensity, :precipProbability, :temperature,
    :apparentTemperature, :dewPoint, :humidity, :pressure, :windSpeed, :windGust,
    :windBearing, :cloudCover, :uvIndex, :visibility, :ozone]
end
