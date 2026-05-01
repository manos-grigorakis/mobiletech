import { computed, type ComputedRef } from 'vue'

export function useStockStatus(stock: ComputedRef<number>) {
  const stockStatus = computed(() => {
    if (stock.value === 0) return 'out'
    if (stock.value <= 5) return 'low'
    return 'in'
  })

  const stockLabel = computed(() => {
    if (stock.value === 0) return 'Out of stock'
    if (stock.value <= 5) return `Only ${stock.value} left`
    return 'In Stock'
  })

  const stockClasses = computed(() => {
    switch (stockStatus.value) {
      case 'in':
        return 'text-green-600'
      case 'low':
        return 'text-yellow-600'
      case 'out':
        return 'text-red-600'
      default:
        return 'text-gray-500'
    }
  })

  return { stockStatus, stockLabel, stockClasses }
}
