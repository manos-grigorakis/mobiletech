export const formatPrice = (value: number) => {
  return new Intl.NumberFormat('el-GR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(value)
}
