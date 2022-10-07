# EffectLibEnhancer
An addon of EffectLib, added some effects.

PhysicsParticleEffect

PhysicsArmorStandEffect

# ScreenShot
![녹화_2022_10_06_22_47_55_392](https://user-images.githubusercontent.com/114675706/194577709-2e8761f0-6866-44bd-98c3-c51c47dbf781.gif)
![녹화_2022_10_07_23_14_34_435](https://user-images.githubusercontent.com/114675706/194577518-7ef87bfc-e361-4ad8-ae24-96c96cff22c0.gif)

# Examples in MagicSpells

//PhysicsArmorStandEffect

EffectLibEnchncer:

    spell-class: ".instant.DummySpell"
    effects:
        1:
            position: caster
            effect: effectlib
            effectlib:
                class: PhysicsArmorStandEffect
                gravity: 4
                amount: 4
                orientPitch: true
                startPitch: 30to60
                startYaw: -20to20
                objectDuration: 140
                velocity: 10to30
                bounceAmplifier: 0.9
                bounceAmount: 4
                
# Warning
do not call PhysicsArmorStandEffect effect asynchronously please.
it causes Exception.

