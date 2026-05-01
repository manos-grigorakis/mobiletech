import { useCartStore } from '@/stores/cart'
import type { Product } from '@/types/product'
import { computed, toValue, type MaybeRefOrGetter } from 'vue'

export function useCartItem(product: MaybeRefOrGetter<Product | undefined>) {
  const cart = useCartStore()

  const cartItem = computed(() => {
    const p = toValue(product)
    return p ? cart.items.find((i) => i.id === p.id) : undefined
  })

  const isMaxStock = computed(() => {
    const p = toValue(product)
    return p ? (cartItem.value?.quantity ?? 0) >= p.stock : false
  })

  const isOutOfStock = computed(() => {
    const p = toValue(product)
    return p ? p.stock === 0 : false
  })

  const isDisabled = computed(() => isOutOfStock.value || isMaxStock.value)

  const addToCart = () => {
    const p = toValue(product)
    if (p && !isDisabled.value) cart.addToCart(p)
  }

  return { cartItem, isMaxStock, isOutOfStock, isDisabled, addToCart }
}
