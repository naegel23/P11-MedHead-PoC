// @vitest-environment jsdom

import { afterEach, describe, expect, it, vi } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import App from './App.vue'

describe('App', () => {
  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('affiche le formulaire de recherche MedHead', () => {
    const wrapper = mount(App)

    expect(wrapper.text()).toContain('Hospital Finder')
    expect(wrapper.text()).toContain('Rechercher un hôpital')
    expect(wrapper.find('#longitude').exists()).toBe(true)
    expect(wrapper.find('#latitude').exists()).toBe(true)
    expect(wrapper.find('#distance').exists()).toBe(true)
    expect(wrapper.find('#speciality').exists()).toBe(true)
  })

  it('affiche les hôpitaux retournés par le backend', async () => {
    const response = [
      {
        hopitalDestination: {
          id: 1,
          name: 'Test Hospital',
          address: '1 Hospital Road',
          freebed: 12,
        },
        distanceGoal: 15.4,
        durationDrive: 1200,
      },
    ]

    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: vi.fn().mockResolvedValue(response),
      }),
    )

    const wrapper = mount(App)

    await wrapper.find('form').trigger('submit')
    await flushPromises()

    expect(fetch).toHaveBeenCalledWith(
      '/api/nearest/-2.0115245/52.00514984/100/pharmacology',
    )

    expect(wrapper.text()).toContain('Test Hospital')
    expect(wrapper.text()).toContain('12 lits disponibles')
    expect(wrapper.text()).toContain('15.4 km')
    expect(wrapper.text()).toContain('20 min')
  })
})
