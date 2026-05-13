export const FormatDate = (date: string) => {
  return new Date(date).toLocaleString(navigator.language)
}
