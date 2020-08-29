defmodule ElixirServer do
  @moduledoc """
  ElixirServer keeps the contexts that define your domain
  and business logic.

  Contexts are also responsible for managing your data, regardless
  if it comes from the database, an external API or others.
  """

  def repr_module do
    quote do
      alias ElixirServer.CurrentlyReprModule, as: CurrentlyReprModule
    end
  end

  defmacro __using__(which) when is_atom(which) do
    apply(__MODULE__, which, [])
  end
end
