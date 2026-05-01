import type { CartItem } from '@/types/cart-items'
import type { Product } from '@/types/product'
import { defineStore } from 'pinia'

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: [] as CartItem[],
  }),

  persist: true,

  getters: {
    totalPrice: (state): number =>
      state.items.reduce((total, item) => total + item.price * item.quantity, 0),
    totalItems: (state): number => state.items.reduce((total, item) => total + item.quantity, 0),
  },

  actions: {
    addToCart(product: Product) {
      const existing = this.items.find((i) => i.id === product.id)

      if (existing) {
        existing.quantity++
      } else {
        this.items.push({ ...product, quantity: 1 })
      }
    },

    removeFromCart(productId: number) {
      this.items = this.items.filter((i) => i.id !== productId)
    },

    updateQuantity(productId: number, quantity: number) {
      const item = this.items.find((i) => i.id === productId)
      if (!item) return

      if (quantity <= 0) {
        this.removeFromCart(productId)
        return
      }

      if (quantity > item.stock) {
        item.quantity = item.stock
      } else {
        item.quantity = quantity
      }
    },

    clearCart() {
      this.items = []
    },
  },
})
